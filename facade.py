from packer import Packer
import util_functions
def __main__():
    best_solution = None
    min_score = float('inf')
    p = Packer(items=[])
    for i in range(15,25):
        p.pack(sort_f=util_functions.clustered_height_area_sort, place_f=util_functions.rs_merit_function, l=i)
        if min_score > util_functions.number_of_bins(p.get_solution()):
            min_score = util_functions.number_of_bins(p.get_solution())
            best_solution = p.get_solution()
