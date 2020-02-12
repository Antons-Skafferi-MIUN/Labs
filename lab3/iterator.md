The Iterator design pattern offers the possibility of traversing and accessing elements in an aggregate object while keeping the internal structure of the aggregate hidden. The Iterator pattern also allows for implementing different ways of traversing without having to define them in the aggregates own interface and, further, for simultaneous traversals over an aggregate. As a minimum, an iterators interface includes functions for initializing the current element to the first element in the range, moving to the next element, returning the current element and checking whether the traversal is completed. There are two general ways of handling who controls the iteration, it can either be the iterator itself or the object using the iterator. In the first case the iterator is called an internal iterator, and in the second case, the iterator is known as an external iterator. 




