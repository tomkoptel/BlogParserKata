# Kata that demonstrates usage Kotlin coroutines

What application does:
* Creates 3 network calls executed in parallel.
* Grabs the most first 10-th character from response stream.
* Grabs every 10-th character from response stream.
* Builds simple histogram of word frequencies.
* Displays data in RecyclerView.
* Survives configuration changes (e.g. rotation during network call).
* Demonstrates hermetic setup in UI tests.
* Makes sure Espresso waits for the coroutines to be finished.

The goals to be solved:

- [x] Make the calls delivered with coroutines.
- [x] Use Kotlin sequence API to work out with InputStream.
- [x] Make Espresso aware of running coroutines.

![Application Demo](https://github.com/tomkoptel/BlogParserKata/blob/master/app.gif?raw=true)

