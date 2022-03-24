import { Component, Vue, Inject } from 'vue-property-decorator';
import { required } from 'vuelidate/lib/validators';

import { IAddress, Address } from '@/shared/model/address.model';
import AddressService from './address.service';

const validations: any = {
  address: {
    addressType: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class AddressUpdate extends Vue {
  @Inject('addressService')
  private addressService: () => AddressService;

  public address: IAddress = new Address();
  public cities: string[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressId) {
        vm.retrieveAddress(to.params.addressId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.address.id) {
      this.addressService()
        .update(this.address)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('vuerealApp.address.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.addressService()
        .create(this.address)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('vuerealApp.address.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Sucess',
            variant: 'sucess',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveAddress(addressId): void {
    this.addressService()
      .find(addressId)
      .then(res => {
        this.address = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cities = ['Beijing', 'Shanghai', 'Guangzhou', 'Shenzhen'];
  }
}
