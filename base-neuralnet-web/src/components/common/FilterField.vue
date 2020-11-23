 <template>
  <div class="container">
    <template v-if="item.type === 'Text'">
      <el-button icon="el-icon-close" circle size="medium" @click="handleCancelCommand(item.value)"></el-button>
      <el-input
        v-model="query[item.value]"
        :placeholder="'搜索' + item.name"
        size="medium"
        @input="changeQuery(item.value, query[item.value])"
      >
        <el-button slot="append" icon="el-icon-search"></el-button>
      </el-input>
    </template>
    <template v-else-if="item.type === 'Select'">
      <el-button icon="el-icon-close" circle size="medium" @click="handleCancelCommand(item.value)"></el-button>
      <el-select
        v-model="query[item.value]"
        placeholder="请选择"
        size="medium"
        @change="changeQuery(item.value, query[item.value])"
      >
        <el-option v-for="(i, index) in item.data" :key="index" :label="i" :value="i"></el-option>
      </el-select>
    </template>
    <template v-else-if="item.type === 'Date'">
      <el-button icon="el-icon-close" circle size="medium" @click="handleCancelCommand(item.value)"></el-button>
      <el-date-picker
        v-model="query[item.value]"
        type="datetimerange"
        :picker-options="pickerOptions"
        :change="changeQuery(item.value, query[item.value])"
        range-separator="至"
        size="medium"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="timestamp"
        align="right"
      ></el-date-picker>
    </template>
  </div>
</template>

<script>
export default {
  name: 'FilterField',
  props: ['item', 'query', 'handleCancelCommand', 'changeQuery'],
  // created() {
  //   console.log('item', queryValue)
  // },
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
};
</script>

<style lang="less" scoped>
.container {
  display: flex;
  width: 280px;
  margin-right: 10px;
}
</style>
