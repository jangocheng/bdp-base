import Vue from 'vue';
import echarts from 'echarts';
import Socket from 'sockjs-client';
import stomp from 'stompjs';
import lodash from 'lodash';
import App from './App.vue';
import router from './router';
import store from './store';

import './registerServiceWorker';
// import ajax from './config/config'; // 引入封装的ajax

Vue.config.devtools = true;
Vue.config.productionTip = false;

Vue.prototype.$echarts = echarts; //将echarts注册成Vue的全局属性
// Vue.prototype.ajaxs = ajax;
Vue.prototype.Socket = Socket;
Vue.prototype.Stomp = stomp;
Vue.prototype._lodash = lodash;

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
