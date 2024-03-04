import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'src/app.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(
    const ProviderScope(
      child: MyApp(color: Colors.blue),
    ),
  );
}

@pragma('vm:entry-point')
void topMain() {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(
    const ProviderScope(
      child: MyApp(color: Colors.green),
    ),
  );
}

@pragma('vm:entry-point')
void bottomMain() {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(
    const ProviderScope(
      child: MyApp(color: Colors.purple),
    ),
  );
}
