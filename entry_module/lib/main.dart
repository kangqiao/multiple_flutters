import 'package:biz_module01/main.dart' as biz_module_01;
import 'package:biz_module02/main.dart' as biz_module_02;

@pragma('vm:entry-point')
void bizModule02() => biz_module_02.main();

void main() => biz_module_01.main();

@pragma('vm:entry-point')
void topMain() => biz_module_01.topMain();

@pragma('vm:entry-point')
void bottomMain() => biz_module_01.bottomMain();
