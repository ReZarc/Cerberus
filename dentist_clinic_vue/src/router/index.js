import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'admin',
    component: () => import( '../views/admin')
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import( '../views/admin/index.vue'),
    children:[
      {
        path: '/admin/adminUser',
        component: () => import( '@/views/admin/admin/adminUser.vue'),
      },
      {
        path: '/admin/adminDoc',
        component: () => import( '@/views/admin/admin/adminDoc.vue'),
      },
      {
        path: '/admin/adminAppoint',
        component: () => import( '@/views/admin/admin/adminAppoint.vue'),
      },
      {
        path: '/admin/adminSurgery',
        component: () => import( '@/views/admin/admin/adminSurgery.vue'),
      },
      {
        path: '/admin/adminOrder',
        component: () => import( '@/views/admin/admin/adminOrder.vue'),
      },
      {
        path: '/admin/adminDrug',
        component: () => import( '@/views/admin/admin/adminDrug.vue'),
      },
      {
        path: '/admin/adminPatientOrder',
        component: () => import( '../views/admin/admin/adminPatientOrder.vue'),
      },
      {
        path: '/admin/adminDrugOrder',
        component: () => import( '../views/admin/admin/adminDrugOrder.vue'),
      },
      {
        path: '/admin/adminSurgeryOrder',
        component: () => import( '../views/admin/admin/adminSurgeryOrder.vue'),
      },
    ]

  },
  {
    path: '/login',
    name: 'login',
    component: () => import( '../views/login')
  }, {
    path: '/register',
    name: 'register',
    component: () => import( '../views/register')
  },
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
