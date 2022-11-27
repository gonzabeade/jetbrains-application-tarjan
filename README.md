

# Jetbrains application (1/3)

Changes made to the original implementation: 

1. Removed wildcards from `List<List<Node<?>>> tarjan(List<Node<?>> graph)` method and parametrized the method over a generic type `T`.The method definition is now `public static <T> List<List<Node<T>>> tarjan(List<Node<T>> graph)`. # jetbrains-application-tarjan
