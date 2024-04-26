import 'package:biz_module02/assets/assets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'counter_provider.dart';

class MyApp extends StatelessWidget {
  const MyApp({super.key, required this.color});

  final MaterialColor color;

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Biz Module 02',
      theme: ThemeData(
          colorSchemeSeed: color,
          useMaterial3: true,
          appBarTheme: AppBarTheme(
            backgroundColor: color,
            foregroundColor: Colors.white,
            elevation: 8,
          )),
      home: const HomePage(),
    );
  }
}

/// The actual content displayed by the module.
///
/// This widget displays info about the state of a counter and how much room (in
/// logical pixels) it's been given. It also offers buttons to increment the
/// counter and (optionally) close the Flutter view.
class HomePage extends ConsumerWidget {
  final bool showExit;

  const HomePage({super.key, this.showExit = false});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final counter = ref.watch(counterProvider);
    return SizedBox.expand(
      child: Stack(
        children: [
          Positioned.fill(
            child: DecoratedBox(
              decoration: BoxDecoration(
                color: Theme.of(context).scaffoldBackgroundColor,
              ),
            ),
          ),
          const Positioned.fill(
            child: Opacity(
              opacity: .25,
              child: FittedBox(
                fit: BoxFit.cover,
                child: FlutterLogo(),
              ),
            ),
          ),
          Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.asset(
                  Assets.imgAvatar,
                  package: Assets.package,
                  width: 80,
                  height: 80,
                ),
                Text(
                  'Biz Module02 times:',
                  style: Theme.of(context).textTheme.headlineSmall,
                ),
                Text(
                  'Count: ${counter.count}',
                  style: Theme.of(context).textTheme.headlineSmall,
                ),
                const SizedBox(height: 16),
                ElevatedButton(
                  onPressed: () {
                    ref.read(counterProvider.notifier).increment();
                  },
                  child: const Text('Add'),
                ),
                const SizedBox(height: 16),
                ElevatedButton(
                  onPressed: () {
                    ref.read(counterProvider.notifier).next();
                  },
                  child: const Text('Next'),
                ),
                if (showExit) ...[
                  const SizedBox(height: 16),
                  ElevatedButton(
                    onPressed: () => SystemNavigator.pop(animated: true),
                    child: const Text('Exit this screen'),
                  ),
                ],
              ],
            ),
          ),
        ],
      ),
    );
  }
}
