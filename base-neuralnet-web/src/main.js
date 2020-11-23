import Vue from 'vue';
import '@/icons'; // icon
import '@/permission'; // permission control
import 'normalize.css/normalize.css'; // A modern alternative to CSS resets
import ElementUI from 'element-ui';

import lodash from 'lodash';
import 'element-ui/lib/theme-chalk/index.css';
import echarts from 'echarts';

import '@/styles/index.scss'; // global css
import App from './App';
import router from './router';
import store from './store';

import Datejs from './utils/Datejs';
import Filter from './utils/filter';

Vue.use(ElementUI);

Vue.config.productionTip = false;
Vue.prototype.$echarts = echarts;
Vue.prototype._ = lodash;
Vue.prototype.$datejs = Datejs;

Vue.filter('overdueStatus', Filter.overdueStatus);
Vue.filter('staffStatus', Filter.staffStatus);
Vue.filter('contactType', Filter.contactType);

window.vm = new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
});
