import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDepartment } from '@/shared/model/department.model';

import DepartmentService from './department.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Department extends Vue {
  @Inject('departmentService') private departmentService: () => DepartmentService;
  private removeId: number = null;

  public departments: IDepartment[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDepartments();
  }

  public clear(): void {
    this.retrieveAllDepartments();
  }

  public retrieveAllDepartments(): void {
    this.isFetching = true;
    this.departmentService()
      .retrieve()
      .then(
        res => {
          this.departments = res.data;
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

  public prepareRemove(instance: IDepartment): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDepartment(): void {
    this.departmentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('vuerealApp.department.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDepartments();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
