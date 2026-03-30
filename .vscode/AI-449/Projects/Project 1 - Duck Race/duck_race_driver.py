from duck_race_graph import duck_graph
from duck_search import depth_search, cost_search, heuristic_search
def init():
    duck_amount = int(input("Please provide a number of the amount of ducks: "))
    duck_deepness = int(input("Please provide the length of the graph: "))
    flag_duck = int(input("Among these ducks which is the flag duck? \nPlease provide the number of this duck: "))
    duck_capacity = int(input("The amount of energy all ducks have: "))
    duck_tree = duck_graph(duck_amount, duck_deepness, flag_duck, duck_capacity)
    print("Legend:\n* = duck occupied point\n^ = goal\n[] = adjacent points in x axis\n{} = adjacent points in y axis")
    print(duck_tree.river_list)
    deep_search = depth_search(duck_tree)
    cost_effective_search = cost_search(duck_tree)
    a_search = heuristic_search(duck_tree)
    print(search(deep_search, cost_effective_search, a_search))
def search(depth_search, cost_search, a_search):
    s = "Output: " 
    deep_search_result = depth_search.search()
    if(deep_search_result == "No solution"):
        cost_search_result = cost_search.search()
        if(cost_search_result == "No solution"):
            s += a_search.search()
        else:
            s += cost_search_result
    else:
        s += deep_search_result
    return s
init()