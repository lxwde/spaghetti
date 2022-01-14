import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Department = () => import('@/entities/department/department.vue');
// prettier-ignore
const DepartmentUpdate = () => import('@/entities/department/department-update.vue');
// prettier-ignore
const DepartmentDetails = () => import('@/entities/department/department-details.vue');
// prettier-ignore
const Employee = () => import('@/entities/employee/employee.vue');
// prettier-ignore
const EmployeeUpdate = () => import('@/entities/employee/employee-update.vue');
// prettier-ignore
const EmployeeDetails = () => import('@/entities/employee/employee-details.vue');
// prettier-ignore
const Job = () => import('@/entities/job/job.vue');
// prettier-ignore
const JobUpdate = () => import('@/entities/job/job-update.vue');
// prettier-ignore
const JobDetails = () => import('@/entities/job/job-details.vue');
// prettier-ignore
const JobHistory = () => import('@/entities/job-history/job-history.vue');
// prettier-ignore
const JobHistoryUpdate = () => import('@/entities/job-history/job-history-update.vue');
// prettier-ignore
const JobHistoryDetails = () => import('@/entities/job-history/job-history-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/department',
    name: 'Department',
    component: Department,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/department/new',
    name: 'DepartmentCreate',
    component: DepartmentUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/department/:departmentId/edit',
    name: 'DepartmentEdit',
    component: DepartmentUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/department/:departmentId/view',
    name: 'DepartmentView',
    component: DepartmentDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/employee',
    name: 'Employee',
    component: Employee,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/employee/new',
    name: 'EmployeeCreate',
    component: EmployeeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/employee/:employeeId/edit',
    name: 'EmployeeEdit',
    component: EmployeeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/employee/:employeeId/view',
    name: 'EmployeeView',
    component: EmployeeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job',
    name: 'Job',
    component: Job,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job/new',
    name: 'JobCreate',
    component: JobUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job/:jobId/edit',
    name: 'JobEdit',
    component: JobUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job/:jobId/view',
    name: 'JobView',
    component: JobDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job-history',
    name: 'JobHistory',
    component: JobHistory,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job-history/new',
    name: 'JobHistoryCreate',
    component: JobHistoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job-history/:jobHistoryId/edit',
    name: 'JobHistoryEdit',
    component: JobHistoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/job-history/:jobHistoryId/view',
    name: 'JobHistoryView',
    component: JobHistoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
