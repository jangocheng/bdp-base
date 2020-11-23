export const tableDateWidthMixins = {
  props: {
    startDate: {},
    endDate: {},
    time: { type: String },
    payType: { type: String },
    osType: { type: String },
    channel: { type: String },
    popuChannel: { type: String },
    quotaStatus: { type: String },
    approveStatus: { type: String },
    sjtType: { type: String },
    payWays: { type: String },
  },
  methods: {
    tableDateWidth(param) {
      return param === 'week' ? '240px' : '120px';
    },
  },
  data() {
    return {
      tableData: [], //表格数据
      pageNum: 1, //当前页数
      total: 0, //总条数
      pageSize: 10, //每页条数,默认
      loading: true,
    };
  },
};
