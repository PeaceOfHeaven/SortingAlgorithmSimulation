# SortingAlgorithmSimulation (Refactored)

Simulating sort algorithms : **Bubble Sort, Insertion Sort, Binary Insertion Sort, Selection Sort, Quick Sort, Merge Sort**.
This application is the one refactoring from my project at my university.

I'm using **[Model View Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)** architectural pattern which improve the **separation of concerns in presentation logic** and make **unit testing more easy** (ex: if we want to unit test our presenter we just need to **mock or fake our input to test our unit ( [Mockito](http://site.mockito.org/), [Roboelectric](http://robolectric.org/), ...)** our model and view, presenter for unit test model and the same thing for view). I'm looking for MVP because i honestly want to write **[clean](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882), maintainable and testable code**. **I currently have not unit tested it yet!!!**

**[Overdraw](https://developer.android.com/studio/profile/dev-options-overdraw.html)** avoid overdraw could help your app perf

**[MP Android Chart](https://github.com/PhilJay/MPAndroidChart)** is used to show how the execution time of a program scales with the input data (as input gets bigger, how much longer will the program take. Just a little bit? A lot longer?) aka Big O notation which is expressed as O(N) 

I have used one helpful tool called **[HPROF](https://developer.android.com/studio/profile/am-hprof.html)** to identify which object types might be involved in memory leaks. But i have fixed about 80% this leaky bug. I currently want to fix this bug


- **Next goals**

|               |   Activity    |    
| ------------- |:-------------:|
|1| Unit and Integration testing |
|2| Fixing memory leak |



