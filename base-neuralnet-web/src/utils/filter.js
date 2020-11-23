class Filter {
  static overdueStatus(parmas) {
    if (!parmas) return '';
    if (parmas === '0') {
      return '无逾期';
    } else if (parmas === '1') {
      return '有逾期';
    }
  }

  static staffStatus(parmas) {
    if (!parmas) return '';
    if (parmas === '0') {
      return '非员工';
    } else if (parmas === '1') {
      return '员工';
    }
  }

  static contactType(parmas) {
    if (!parmas) return '';
    if (parmas === '1') {
      return '普通联系人';
    } else if (parmas === '2') {
      return '紧急联系人';
    }
  }
}

export default Filter;
