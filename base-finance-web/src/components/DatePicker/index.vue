<template>
  <div>
    <el-date-picker
      v-model="Date"
      :picker-options="pickerOptions"
      align="right"
      class="date-picker-container"
      end-placeholder="结束月份"
      range-separator="至"
      size="medium"
      start-placeholder="开始月份"
      type="monthrange"
      unlink-panels
    />
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'

  @Component({
    name: 'DatePicker'
  })
export default class extends Vue {
    @Prop({ required: true }) private currDate!: [string, string]

    get Date() {
      return this.currDate
    }

    set Date(value) {
      this.$emit('update:currDate', value)
    }
}
</script>

<style lang="scss" scoped>
  .date-picker-container {
    width: 400px;
  }
</style>
