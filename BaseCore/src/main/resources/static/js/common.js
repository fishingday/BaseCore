/**
 * common JS
 * 
 */
function SimpleDateTimeFormat(date, pattern) {
  var dateString = pattern.replace(/(yyyy|MM|dd|HH|mm|ss|SSS)/g, function(match) {
    var matchString = "";
    switch(match) {
      case "yyyy":
        matchString = date.getFullYear();
        break;
      case "MM":
        matchString = date.getMonth() + 1;
        break;
      case "dd":
        matchString = date.getDate();
        break;
      case "HH":
        matchString = date.getHours();
        break;
      case "mm":
        matchString = date.getMinutes();
        break;
      case "ss":
        matchString = date.getSeconds();
        break;
      case "SSS":
        matchString = date.getMilliseconds();
        break;
      default :
        matchString = match;
        break;
    }
    if (match == "SSS") {
      if (matchString < 10) {
        matchString = "00" + matchString;
      } else if (matchString < 100) {
        matchString = "0" + matchString;
      }
    } else {
      if ((typeof(matchString) == "number" && matchString < 10)) {
        matchString = "0" + matchString;
      }
    }
    return matchString;
  });

  return dateString;
}