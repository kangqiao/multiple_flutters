import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'models/export.dart';

final counterProvider =
    StateNotifierProvider.autoDispose<CounterNotifier, Counter>(
  (ref) => CounterNotifier(),
  name: 'counterProvider',
);

class CounterNotifier extends StateNotifier<Counter> {
  final _channel = const MethodChannel('com.zp.multiple_flutters/counter');

  CounterNotifier() : super(Counter()) {
    _channel.setMethodCallHandler(_handleMessage);
    _channel.invokeMethod<int>('getCount').then((value) {
      if (value is int) {
        state = Counter(count: value);
      }
    });
  }

  void increment() {
    _channel.invokeMethod<void>('incrementCount');
  }

  void next() {
    _channel.invokeMethod<void>('next');
  }

  Future<dynamic> _handleMessage(MethodCall call) async {
    if (call.method == 'setCount') {
      if (call.arguments is int) {
        state = Counter(count: call.arguments);
      }
    }
  }
}
