import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:module/src/models/counter/counter.dart';

final counterProvider =
    StateNotifierProvider.autoDispose<CounterNotifier, Counter>(
  (ref) => CounterNotifier(),
  name: 'counterProvider',
);

class CounterNotifier extends StateNotifier<Counter> {
  final _channel = const MethodChannel('com.zp.multiple_flutters/counter');

  CounterNotifier() : super(Counter()) {
    _channel.setMethodCallHandler(_handleMessage);
    _channel.invokeMethod<void>('requestCounter');
  }

  void increment() {
    _channel.invokeMethod<void>('incrementCounter');
  }

  Future<dynamic> _handleMessage(MethodCall call) async {
    if (call.method == 'reportCounter') {
      if (call.arguments is int) {
        state = Counter(count: call.arguments);
      }
    }
  }
}
