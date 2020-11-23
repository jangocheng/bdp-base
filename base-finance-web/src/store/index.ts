import Vue from 'vue'
import Vuex from 'vuex'
import {IAppState} from './modules/app'
import {ITagsViewState} from './modules/tags-view'
import {IErrorLogState} from './modules/error-log'
import {IPermissionState} from './modules/permission'
import {IDictState} from './modules/dict'

Vue.use(Vuex)

export interface IRootState {
  app: IAppState
  tagsView: ITagsViewState
  errorLog: IErrorLogState
  permission: IPermissionState
  dict: IDictState
}

// Declare empty store first, dynamically register all modules later.
export default new Vuex.Store<IRootState>({})
