from river_nodes import river_node
from duck import duck
class duck_graph:
    duck_amount = None
    duck_deepness = None
    flag_duck = None
    duck_capacity = None
    duck_list = None
    river_list = None
    def __init__(self, da, dd, fd, dc):
        self.duck_amount = da
        self.duck_deepness = dd #how deep the graph is
        self.flag_duck = fd #duck that goes to the flag
        self.duck_capacity = dc #energy each duck has, assuming all ducks are at max energy
        self.duck_list = (list(duck(dc, i == fd) for i in range(0, self.duck_amount)))
        self.river_list = (list(river_node(i) for i in range(0, self.duck_amount*dd)))
        for i in self.river_list:
            if (i.node_num%dd == 0):
                i.setDuck(self.duck_list[i.node_num//dd])
                self.duck_list[i.node_num//dd].position = i
                if(i.duck.isFlag):
                    self.river_list[i.node_num+dd-1].set_goal(True)
            adj_x_node_list = list()
            if(i.node_num > dd*(i.node_num//dd)):
                adj_x_node_list.append(self.river_list[i.node_num-1])
            try:
                if((i.node_num+1 < dd*(1 + i.node_num//dd)) or i.node_num == 0):
                    adj_x_node_list.append(self.river_list[i.node_num+1])
            except:
                pass
            i.set_adjacent_nodes_x(adj_x_node_list)
            adj_y_node_list = list()
            if(i.node_num > dd):
                adj_y_node_list.append(self.river_list[i.node_num-dd])
            try:
                adj_y_node_list.append(self.river_list[i.node_num+dd])
            except:
                pass
            i.set_adjacent_nodes_y(adj_y_node_list)
