import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readData(path:String, name: String): List<String> = File(path, "$name.txt").readLines()

fun readInput(name: String): List<String> = readData("src/resources/input", name)

fun readTestInput(name: String): List<String> = readData("src/resources/test", name)

fun readInputInt(name: String) = readInput(name).map { x: String -> x.toInt() }

fun readTestInputInt(name: String) = readTestInput(name).map { x: String -> x.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
