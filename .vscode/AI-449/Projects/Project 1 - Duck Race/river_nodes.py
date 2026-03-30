class river_node:
    node_num = None
    duck = None
    duck_occupied = None
    goal = None
    adjacent_nodes_x = None #list of nodes, length of 2 or 1. Set after all nodes are made.
    adjacent_nodes_y = None #list of nodes, length of 2 or 1. Set after all nodes are made.
    def __init__(self, nn):
        self.node_num = nn
    def set_adjacent_nodes_x(self, anl):
        self.adjacent_nodes_x = anl
    def set_adjacent_nodes_y(self, anl):
        self.adjacent_nodes_y = anl
    def is_adjacent_x(self, node):
        for i in self.adjacent_nodes_x:
            if(node.equals(i)):
                return True
    def is_adjacent_and_has_duck_y(self, node):
        for i in self.adjacent_nodes_y:
            if(node.equals(i) and node.duck_occupied):
                return True
        return False
    def equals(self, other):
        return self.node_num == other.node_num
    def setDuck(self, duck):
        self.duck = duck
        if(self.duck is None):
            self.duck_occupied = False
        else:
            self.duck_occupied = True
    def set_goal(self, goal):
        self.goal = goal
    def __repr__(self):
        s = str(self.node_num)
        if(self.duck_occupied):
            s += "*"
        if(self.goal):
            s += "^"
        s += "["
        s += self.str_adj(self.adjacent_nodes_x)
        s += "]"
        s += "{"
        s += self.str_adj(self.adjacent_nodes_y)
        s += "}"
        return s
    def str_adj(self, adjacent_nodes):
        s = ""
        for i in range(0, len(adjacent_nodes)):
            nodei = adjacent_nodes[i]
            s += str(nodei.node_num)
            if(nodei.duck_occupied):
                s += "*"
            if(nodei.goal):
                s += "^"
            if(i < len(adjacent_nodes) - 1):
                s += ", "
        return s
    