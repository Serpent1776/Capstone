from river_nodes import river_node
class duck:
    capacity = None #energy max
    energy = None
    cantransfer = None #only up or down while next to a duck
    canMove = None # only left or right
    position = None #node where the duck is
    isFlag = None
    hasFlag = None
    priority = None
    moved_times = None
    def __init__(self, cap, flag):
        self.capacity = cap
        self.energy = cap
        self.cantransfer = True
        self.canMove = True
        self.isFlag = flag
        self.hasFlag = False
        self.priority = None
        self.moved_times = 0
    def move(self, movement):
        """moves() -> moves a duck to a node"""
        if(self.canMove):
            self.energy -= 1
            if(self.position.is_adjacent_x(movement)):
                movement.setDuck(self.position.duck)
                self.position.setDuck(None)
                self.position = movement
            else:
                self.energy += 1
            if(self.energy == 0):
                self.canMove = False
                self.cantransfer = False
    def transfer(self, node):
        """transfer() -> transfer energy to a duck on node, if a duck is on it"""
        if(self.cantransfer):
            self.energy -= 1
            if(self.position.is_adjacent_and_has_duck_y(node)):
                if(node.duck.energy < node.duck.capacity):
                    node.duck.energy += 1
                    if(node.duck.energy > 0): #reactivates duck movement and transfer when energy is gained
                        node.duck.canMove = True
                        node.duck.cantransfer = True
                else:
                    self.energy += 1
            else:
                self.energy += 1
            if(self.energy == 0):
                self.canMove = False
                self.cantransfer = False
    def get_flag(self):
        if(self.position.goal):
            self.hasFlag = True
    