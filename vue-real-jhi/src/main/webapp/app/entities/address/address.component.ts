import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAddress } from '@/shared/model/address.model';

import AddressService from './address.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Address extends Vue {
  @Inject('addressService')
  private addressService: () => AddressService;

  private removeId: number = null;

  public addresses: IAddress[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAddresses();
  }

  public clear(): void {
    this.retrieveAllAddresses();
  }

  public retrieveAllAddresses(): void {
    this.isFetching = true;
    this.addressService()
      .retrieve()
      .then(
        res => {
          this.addresses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IAddress): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAddress(): void {
    this.addressService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('vuerealApp.address.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAddresses();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
