import Vue, {DirectiveOptions} from 'vue'

import 'normalize.css'
import ElementUI from 'element-ui'
import SvgIcon from 'vue-svgicon'

import '@/styles/element-variables.scss'
import '@/styles/index.scss'

import _ from 'lodash'
import App from '@/App.vue'
import store from '@/store'
import {AppModule} from '@/store/modules/app'
import router from '@/router'
import '@/icons/components'
import '@/permission'
import '@/utils/error-log'
import '@/pwa/register-service-worker'
import * as directives from '@/directives'
import * as filters from '@/filters'
import {DictMixin} from './mixin/DictMixin'

Vue.prototype._ = _

Vue.use(ElementUI, {
  size: AppModule.size //
})

Vue.use(SvgIcon, {
  tagName: 'svg-icon',
  defaultWidth: '1em',
  defaultHeight: '1em'
})

Vue.mixin(DictMixin)

// 注册全局directives
Object.keys(directives).forEach(key => {
  Vue.directive(key, (directives as { [key: string]: DirectiveOptions })[key])
})

// 注册全局filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, (filters as { [key: string]: Function })[key])
})

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
