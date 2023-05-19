fun main() {
    val parkingLot = ParkingLot()
    do {
        val input = readln().split(" ")
        parkingLot.initialize(input)
    } while (!parkingLot.exit)
}

class Spot(val car: MutableList<Car>) {
    var occupied = false

    fun occupy(color: String, regNum: String, spot: Int) {
        car.add(Car(color.lowercase(), regNum, spot))
        occupied = true
        println("$color car parked in spot ${spot + 1}.")
    }
    fun leave(spot: Int) {
        for (i in car) {
            if (i.spot == spot) {
                car.remove(i)
                break
            }
        }
        occupied = false
    }
}

class ParkingLot {
    private var created = false
    var exit = false
    val cars = mutableListOf<Car>()
    private var spots = MutableList(0) { Spot(cars) }
    fun initialize(input: List<String>) {
        when (input[0]) {
            "create" -> {
                val num = input.last().toInt()
                if (num > 0) {
                    cars.clear()
                    spots.clear()
                    spots = MutableList(num) { Spot(cars) }
                    println("Created a parking lot with $num spots.")
                    created = true
                }
            }
            "exit" -> exit = true
            else -> if (created) {
                when (input[0]) {
                    "park" -> {
                        var count = 0
                        for (spot in spots) {
                            if (!spot.occupied) {
                                spot.occupy(input[2], input[1], spots.indexOf(spot))
                                break
                            } else {
                                count++
                                continue
                            }
                        }

                        when (count) {
                            spots.size -> println("Sorry, the parking lot is full.")
                        }
                    }
                    "leave" -> {
                        val index = input[1].toInt() - 1
                        if (spots[index].occupied) {
                            spots[index].leave(index)
                            println("Spot ${index + 1} is free.")
                        } else {
                            println("There is no car in spot ${index + 1}.")
                        }
                    }
                    "status" -> {
                        if (cars.isEmpty()) {
                            println("Parking lot is empty.")
                        } else {
                            val listOfCars = cars.sortedWith(compareBy { it.spot })
                            for (i in listOfCars) {
                                println("${i.spot + 1} ${i.regNum} ${i.color}")
                            }
                        }
                    }
                    "reg_by_color" -> {
                        when {
                            cars.isEmpty() -> println("Parking lot is empty.")
                            else -> {
                                cars.sortedWith(compareBy { it.spot })
                                val listOfCars = cars.filter { it.color == input[1].lowercase() }
                                if (listOfCars.isEmpty()) {
                                    println("No cars with color ${input[1]} were found.")
                                } else {
                                    for (i in listOfCars) {
                                        if (listOfCars.indexOf(i) < listOfCars.lastIndex) print("${i.regNum}, ") else  println(i.regNum)
                                    }
                                }
                            }
                        }
                    }
                    "spot_by_color" -> {
                        when {
                            cars.isEmpty() -> println("Parking lot is empty.")
                            else -> {
                                cars.sortedWith(compareBy { it.spot })
                                val listOfCars = cars.filter { it.color == input[1].lowercase() }
                                if (listOfCars.isEmpty()) {
                                    println("No cars with color ${input[1]} were found.")
                                } else {
                                    for (i in listOfCars) {
                                        if (listOfCars.indexOf(i) < listOfCars.lastIndex) print("${i.spot + 1}, ") else  println(i.spot + 1)
                                    }
                                }
                            }
                        }
                    }
                    "spot_by_reg" -> {
                        when {
                            cars.isEmpty() -> println("Parking lot is empty.")
                            else -> {
                                cars.sortedWith(compareBy { it.spot })
                                val listOfCars = cars.filter { it.regNum == input[1] }
                                if (listOfCars.isEmpty()) {
                                    println("No cars with registration number ${input[1]} were found.")
                                } else {
                                    for (i in listOfCars) {
                                        if (listOfCars.indexOf(i) < listOfCars.lastIndex) print("${i.spot + 1}, ") else  println(i.spot + 1)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                println("Sorry, a parking lot has not been created.")
            }
        }
    }
}

data class Car(var color: String, var regNum: String, var spot: Int)