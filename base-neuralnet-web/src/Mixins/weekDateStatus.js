export const weekDateStatus = {
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            },
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            },
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            },
          },
        ],
      },
    };
  },
  methods: {
    weekDateStatus() {
      const datejs = this.$datejs;
      let lastDate = datejs.DateFormatterYMD(this.time_series[1]);
      let startDate = datejs.DateFormatterYMD(this.time_series[0]);
      if (this.time === 'week') {
        // 根据 开始时间 获取下周一的时间
        startDate = datejs.GetDateStr({
          AddDayCount: 1,
          date: datejs.weekDate(startDate)[1], //当前日期周一日期
        });
        // 根据结束时间获取上周末的时间
        lastDate = datejs.GetDateStr({
          AddDayCount: -1,
          date: datejs.weekDate(lastDate)[0], //当前日期周一日期
        });
        // 如果开始时间在结束时间后面
        if (startDate > lastDate) {
          this.$message({
            type: 'warning',
            message: '请修改时间查询条件，保证其区间内含有整个自然周',
          });
          return false;
        }
      } else if (this.time === 'month') {
        // 根据 开始时间 获取下周一的时间
        startDate = datejs.GetDateStr({
          AddDayCount: 1,
          date: datejs.monthDate(startDate)[1], //当前月 月末日期
        });
        // 根据结束时间获取上周末的时间
        lastDate = datejs.GetDateStr({
          AddDayCount: -1,
          date: datejs.monthDate(lastDate)[0], //当前月 月初日期
        });
        // 如果开始时间在结束时间后面
        if (startDate > lastDate) {
          this.$message({
            type: 'warning',
            message: '请修改时间查询条件，保证其区间内含有整个自然月',
          });
          return false;
        }
      }
      return true;
    },
  },
};
