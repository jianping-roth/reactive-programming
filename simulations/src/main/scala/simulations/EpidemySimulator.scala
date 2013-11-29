package simulations

import math.random

class EpidemySimulator extends Simulator {

  def randomBelow(i: Int) = (random * i).toInt

  protected[simulations] object SimConfig {
    val population: Int = 300
    val roomRows: Int = 8
    val roomColumns: Int = 8
    val prevalenceRate = 0.01
    val transmissibilityRate = 40
    val daysToMove = 5
    val incubationDays = 6
    val infectedDaysToDie = 14 - incubationDays
    val deathRate = .25
    val infectedDaysToImmune = 16 - infectedDaysToDie
    val infectedDaysToHealthy = 18 - infectedDaysToImmune
    val up = 0
    val down = 1
    val left = 2
    val right = 3
  }

  import SimConfig._

  val persons: List[Person] = createPeople(population)
  
  // create persons with 1 percent infected.
  def createPeople(num : Int) : List[Person] = {
         val people = for (p <- (1 to num).toList) yield new Person(p)
         // make 1% people infected
     val numSick : Int = (population * prevalenceRate).toInt
         do {
           val idx = randomBelow(population)
              people(idx).infected = true
     } while (people.count(p => p.infected) < numSick)
     
     people.foreach(p => p.start())
         people 
  } 
  
  class Person (val id: Int) {
    var infected = false
    var sick = false
    var immune = false
    var dead = false
       
    // demonstrates random number generation
    var row: Int = randomBelow(roomRows)
    var col: Int = randomBelow(roomColumns)
    
    override def toString() = 
       "id=" + id + " infected=" + infected +" sick=" + sick + " immune=" + immune + " dead=" + dead
    
    def roomHasVisbilySickPeople(rowToCheck: Int, colToCheck: Int) : Boolean = {
        return persons.filter( p =>
          p.id != id && p.row == rowToCheck && p.col == colToCheck && (p.sick) ).length > 0
    }
  
    def roomHasSickPeople() : Boolean = {
        return persons.filter( p =>
          p.id != id && p.row == this.row && p.col == this.col && (p.infected) ).length > 0
    }
        
    def checkInfection() : Boolean = {
       if (dead) false
           if (!infected && roomHasSickPeople()) {
                  if (randomBelow(100) <= transmissibilityRate) {
                          infected = true
                          return true
                  }
           } 
          
           return false
     }
  
    def move() : Unit = {
       if (!dead) {
           afterDelay(randomBelow(daysToMove)) {
	           val whichDir = randomBelow(4)
               var nextRow = row
               var nextCol = col
               if (whichDir == up) { if (row == 0) row = roomRows-1 else row = row - 1 }
               else if (whichDir == down) { nextRow = (row + 1) % roomRows }
               else if (whichDir == left) { if (col == 0) col = roomColumns - 1 else col = col - 1 }
               else if (whichDir == right) { nextCol = (col + 1) % roomColumns }
               
               if (!roomHasVisbilySickPeople(nextRow, nextCol)) {
                   row = nextRow
                   col = nextCol
               }
               
               if (checkInfection()) afterInfection()
               move()
           }
        }
    }
    
    def afterInfection() : Unit = {
        if (!dead) {
            afterDelay(incubationDays) {
                sick = true
                    afterDelay(infectedDaysToDie) {
                      dead = randomBelow(100) < deathRate
                       if (!dead) {
                          afterDelay(infectedDaysToImmune) {
                            immune = true
                            afterDelay(infectedDaysToHealthy) {
                               sick = false
                               immune = false
                               infected = false
                            }
                          }
                       }
                    }
                }
        }
    }
    
    def start() : Unit = {
      if (infected == true)  afterInfection()
      move()
    }
  }
}