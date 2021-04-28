# microbiotools
###### About
This first program of microbiology tools is a simple *test-scrubber*, eliminating unfeasible tests to be conducted on a sample. The sample in question generally
comprises three main parts: **patient information**, **specimen details**, and the list of **requested tests**.

In small microbiology labs it is generally inefficient, or outright uneconomic, to conduct every test requested, especially if there is not enough mass of sample
provided or if the sample was collected too long ago; the exam results can become inconclusive. For patients who have serious illnesses, and the medical professionals
who are caring for them, getting accurate test results matters. This program is designed to streamline the process of weeding out tests that could generate inconclusive
results. It can be compiled and run from the command line through **src/user/TestFilter**.

***DISCLAIMER***: This is a simplified version of what tests a lab would conduct on a daily basis. The full list, and all their dependencies, would require a database backing
this project. This will hopefully be implemented in a future version. 
