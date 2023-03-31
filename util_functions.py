from item import Item
from container import Container
from ep import EP
import math

def clustered_sort(items, l, d, f1, f2):
    cluster_indexes = set()
    clusters = dict()
    ans = []
    for i in items:
        x = math.ceil((l * 100 * f1(i)) / d)
        cluster_indexes.add(x)
        if x in clusters:
            clusters[x].append(i)
        else:
            clusters[x] = [i]
    keys = sorted(cluster_indexes, reverse=True)
    for k in keys:
        ans += sorted(clusters[k], key=f2, reverse=True)
    return ans

    
def get_base_area(i):
    return i.get_width() * i.get_depth()

def get_height(i):
    return i.get_height()

def clustered_area_height_sort(items, l, c):
    return clustered_sort(items=items, l=l, d=c.get_base_area(), f1=get_base_area, f2=get_height)

def clustered_height_area_sort(items, l, c):
    return clustered_sort(items=items, l=l, d=c.get_height(), f2=get_base_area, f1=get_height)
    

def rs_merit_function(item, ep):
    return (ep.get_rs('X') - item.get_width()) + (ep.get_rs('Y') - item.get_depth()) + (ep.get_rs('Z') - item.get_height())

def number_of_bins(solution):
    return len(solution)