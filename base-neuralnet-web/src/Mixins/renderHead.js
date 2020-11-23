export const renderHead = {
  methods: {
    /**
     * 给header头部增加tooltip
     * @param {*} h
     * @param {*} param1
     * @param {*} tooltipMsg
     */
    renderHead(h, { column }, tooltipMsg) {
      return (
        <el-tooltip class="tooltip" effect="light" placement="top">
          <span slot="top">{tooltipMsg}</span>
          <span>column.label</span>
        </el-tooltip>
      );
    },
  },
};
