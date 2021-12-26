import readInput
import readTestInput
import kotlin.Double.Companion.POSITIVE_INFINITY
import java.util.PriorityQueue
import kotlin.math.roundToInt
import kotlin.math.abs

fun main() {
    data class Point(val x: Int, val y: Int) {}
    data class HeapNode(val point: Point, val value: Double) {}

    fun parseInput(input: List<String>): MutableList<MutableList<HeapNode>> {
        val graph: MutableList<MutableList<HeapNode>> = mutableListOf()

        var x = 0
        var y = 0
        for (line: String in input) {
            graph.add(mutableListOf())
            for (char in line) {
                graph.last().add(HeapNode(Point(x, y), char.digitToInt().toDouble()))
                x++

            }
            y++
            x = 0
        }

        return graph
    }


    fun getNeighbours(node: HeapNode, graph: MutableList<MutableList<HeapNode>>): MutableList<HeapNode> {
        val x = node.point.x
        val y = node.point.y

        val neighbours: MutableList<HeapNode> = mutableListOf()
        for (dx in -1..1) {
            for (dy in -1..1) {
                if ((dx == 0 && dy == 0) || !(dx == 0 || dy == 0)) {
                    continue
                }
                val xp = x + dx
                val yp = y + dy
          
                if (yp >= 0 && yp < graph.size && xp >=0 && xp < graph.size) {
                    neighbours.add(graph[yp][xp])
                }
            }
        }
        return neighbours
    }

    fun dijkstrasAlgorithm(start: Point, destination: Point, graph: MutableList<MutableList<HeapNode>>): Double {
        val unvisitedSet: HashSet<Point> = hashSetOf()
        val heap: PriorityQueue<HeapNode> = PriorityQueue {n1: HeapNode, n2 : HeapNode -> n1.value.roundToInt() - n2.value.roundToInt()}
        val d: HashMap<Point, Double> = hashMapOf()
        for (y in 0..(graph.size - 1)) {
            // for (x in 0..(graph[y].size - 1))
            for (x in 0..(graph[y].size - 1)) {
                unvisitedSet.add(graph[y][x].point)
                d[graph[y][x].point] = POSITIVE_INFINITY
            }
        }
        d[start] = 0.0
        heap.add(graph[start.y][start.x])
        
        while (heap.size > 0) {
            val currentNode = heap.poll()
      
            for (neighbour in getNeighbours(currentNode, graph)) {
                if (unvisitedSet.contains(neighbour.point)) {
                    val currentNodeDistance = d[currentNode.point]!!
                    val length = neighbour.value!!
                    val neigbDistance = d[neighbour.point]!!
                    if (neigbDistance > currentNodeDistance + length) {
                        d[neighbour.point] = (currentNodeDistance + length)!!
                        heap.add(HeapNode(neighbour.point, d[neighbour.point]!!))
                    }

                }
            }
 
            unvisitedSet.remove(currentNode.point)
            if (currentNode.point.equals(destination)) {
                return d[destination]!!
            }
        }

        return 4.0
    }

 

    fun part1(graph: MutableList<MutableList<HeapNode>>): Int {
        return dijkstrasAlgorithm(Point(0,0), Point(graph.size - 1, graph.size - 1), graph).roundToInt()

    }

    fun part2(graph: MutableList<MutableList<HeapNode>>): Int {
        // # Somehow len(graph[226]) equals 2000
    val size = graph.size
    for (i in 1..5) {
        for (y in 0..(size - 1)) {
            for (x in 0..(size -1)) {
                val nextX = x + size*i
                var v = graph[y][abs((size)*i - (size - x))].value
                v = v + 1
                val node = HeapNode(Point(nextX, y), if (v.roundToInt() <= 9) v else 1.0)
                // graph[y].add(node)
                graph[y].add(node)
            }
      
        }
    }

    val sizeUpper = size*5 - 1
    for (i in 1..5) {
        for (y in size..sizeUpper) {
            if (y >= graph.size) {
                graph.add(mutableListOf())
            }

            for (x in 0..sizeUpper) {
                var v = graph[y - size][x].value
                v = v + 1
                v = if (v.roundToInt() <= 9) v else 1.0
                val node = HeapNode(Point(x, y + size * i), v)
                graph.last().add(node)
            }
        }
    }
        

    return dijkstrasAlgorithm(Point(0,0), Point(graph.size - 1, graph.size - 1), graph).roundToInt()
}

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day15")
    var data = parseInput(testInput)
    check(part1(data) == 40)

    val input = readInput("Day15")
    data = parseInput(input)
    println(part1(data))
    println((part2(data)))
}
