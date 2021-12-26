import readInput
import readTestInput
fun main() {
    data class Point(var x: Int, var y: Int) {}
    data class Fold(val axis: String, val value: Int) {}

    data class Data(var points: List<Point>, val folds: List<Fold>) {}

    fun parseInput(lines: List<String>): Data {
        val points: MutableList<Point> = mutableListOf()
        var index = 0
        var line = lines[index]
        do {
            val p = line.split(",").map({n: String-> n.trim().toInt()})
            points.add(Point(p[0], p[1]))
           index++
           line = lines[index]
        } while (!line.isEmpty())

        val folds: MutableList<Fold> = mutableListOf()
        // for (i in (index + 1)..(lines.size)) {
        var i = index + 1
        while (i < lines.size) {
            line = lines[i]
            val terms = line.split(' ')
            val foldTerms = terms.last().split('=')
            val fold: Fold = Fold(foldTerms[0], foldTerms[1].toInt())
            folds += fold
            i++
        }

        return Data(points, folds)
    }

    fun foldFn(points: List<Point>, fold: Fold): List<Point> {
        var pointsMap: HashMap<String, Point> = HashMap()
        var (axis, value) = fold

        for (p: Point in points) {
            if ("y".equals(axis) && p.y > value) {
                p.y = p.y - 2 * (p.y - value)
            } 
            else if ("x".equals(axis) && p.x > value) {
                p.x = p.x - 2 * (p.x - value)
            }
        //   pointsMap[p.hashCode()] = p
        pointsMap[p.x.toString() + ":" + p.y.toString()] = p

        }
 
    
        return pointsMap.values.toList()
    }


    fun printPoints(points: List<Point>) {
        val minX = points.map({p -> p.x}).minOrNull()!!
        val maxX = points.map({p -> p.x}).maxOrNull()!!
    
        val minY= points.map({p -> p.y}).minOrNull()!!
        val maxY = points.map({p -> p.y}).maxOrNull()!!
    
        val pointChar = '▖'
        val emptyChar = ' '

        
        val pointsMap: HashMap<String, Point> = HashMap()
        for (p in points) {
            //   pointsMap[p.hashCode()] = p
            pointsMap[p.x.toString() + ":" + p.y.toString()] = p
        }
        
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (pointsMap.containsKey(x.toString() + ":" + y.toString())) print(pointChar) else print(emptyChar)
            }
            println()
        }
    }

    fun part1(data: Data): Int {
        val fold = data.folds[0]
        data.points = foldFn(data.points, fold)
    
        return data.points.size
    }

    fun part2(data: Data): List<Point> {
        // for (i in 1..data.folds.size) {
        //     data.points = foldFn(data.points, data.folds[i])
        // }
        var i = 1
        while (i < data.folds.size) {
            data.points = foldFn(data.points, data.folds[i])
            i++
        }
        return data.points
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day13")
    var data = parseInput(testInput)
    check(part1(data) == 17)

    val input = readInput("Day13")
    data = parseInput(input)
    println(part1(data))
    printPoints((part2(data)))
}
