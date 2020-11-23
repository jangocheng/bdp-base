/**
 * 分页事件
 * @param tabledata
 * @param pageNum 第几页
 * @param pagesize 每页多少条
 */
const pagination = (tabledata, pageNum, pagesize = 10) => {
  if (!Array.isArray(tabledata)) return [];
  pageNum = Number(pageNum);
  pagesize = Number(pagesize);
  let offset = (pageNum - 1) * pagesize; // 当前页第一条的索引
  return offset + pagesize >= tabledata.length
    ? tabledata.slice(offset, tabledata.length)
    : tabledata.slice(offset, offset + pagesize);
};

module.exports = { pagination };
