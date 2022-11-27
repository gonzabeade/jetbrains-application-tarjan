

# Jetbrains application (1/3)

Changes made to the original implementation: 

1. Removed wildcards from `Set<Set<Node<?>>> tarjan(List<Node<?>> graph)` method and parametrized the method over a generic type `T`. The method definition is now `public static <T> List<List<Node<T>>> tarjan(List<Node<T>> graph)`. Rarely should type safety be avoided, and no reason for its omition has been found. 

2. Made the `tarjan` method an instance method. This is because two versions of Tarjan's algorithm were implemented: iterative and recurive. Because both implementation follow a common API, it was easier and more natural to test if these two implementation implemented a `Task` interface. 

3. The `tarjan` method now returns a `Set<Set<Node<T>>>`, according to the mathematical definition of partition. In addition, it is easier and more efficient to query the results of the algorithm, both in practice and in testing. 

4. Implemented a simple `NodeImpl` class which implements the `Node` interface given by the original assignment. Notice that the implementation does not need to override `hashcode` or `equals` because every instance of a node is considered unique and independent of its payload. The pointer reference to the NodeImpl object is sufficient to fully identify a node, and thus is sufficient for the Tarjan implementations to work correctly. 

5. Two versions of the Tarjan algorithm have been implemented. This is because the nature of the algorithm is recursive, yet the JVM is not sufficiently optimized for recursive functions in practice. If the algorithm was to be used in a productie environment, perhaps the necessity of an iterative solution should be studied. Obviously, the implementation for Tarjan's algorithm is neater when implemented recursively. The iterative version of the algorithm is not fully functional, and its problem is identified. The failing test in the source code specifies its source of failure. 

6. The `Stack` class may be replaced by `ArrayDeque` class. `Stack` is a legacy class that is thread-safe,hence it includes a lot of overhead that may be ommited. For the purposes of this project, it has been decided that using the `Stack` class is enough. 
