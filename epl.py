from ep import EP
class EPL:
    def __init__(self, items, extreme_points):
        self.__items = items
        self.__eps = extreme_points
        self.__can_take_projection = {
            'YX' : can_take_projections_yx,
            'YZ' : can_take_projections_yz,
            'XY' : can_take_projections_xy,
            'XZ' : can_take_projections_xz,
            'ZX' : can_take_projections_zx,
            'ZY' : can_take_projections_zy
        }

    def get_possible_eps(self, i):
        ans = set()
        for ep in self.__eps:
            if ep.overlap_check(i):
                ans.add(ep)
        return ans

    def update(self, k):
        max_bound = {
            'YX' : -1,
            'YZ' : -1,
            'XY' : -1,
            'XZ' : -1,
            'ZX' : -1,
            'ZY' : -1
        }

        neweps = {}
        for i in self.__items:
            if self.__can_take_projection['YX'](k, i) and (i.get_x() + i.get_width() > max_bound['YX']):
                neweps['YX'] = (i.get_x() + i.get_width(), k.get_y() + k.get_depth(), k.get_z())
                max_bound['YX'] = i.get_x() + i.get_width()
            if self.__can_take_projection['YZ'](k, i) and (i.get_z() + i.get_height() > max_bound['YZ']):
                neweps['YZ'] = (k.get_x(), k.get_y() + k.get_depth(), i.get_z() + i.get_height())
                max_bound['YZ'] = i.get_z() + i.get_height()
            if self.__can_take_projection['XY'](k, i) and (i.get_y() + i.get_depth() > max_bound['XY']):
                neweps['XY'] = (k.get_x() + k.get_width(), i.get_y() + i.get_depth(), k.get_z())
                max_bound['XY'] = i.get_y() + i.get_depth()
            if self.__can_take_projection['XZ'](k, i) and (i.get_z() + i.get_height() > max_bound['XZ']):
                neweps['XZ'] = (k.get_x() + k.get_width(), k.get_y(), i.get_z() + i.get_height())
                max_bound['XZ'] = i.get_z() + i.get_height()
            if self.__can_take_projection['ZX'](k, i) and (i.get_x() + i.get_width() > max_bound['ZX']):
                neweps['ZX'] = (i.get_x() + i.get_width(), k.get_y(), k.get_z() + k.get_height())
                max_bound['ZX'] = i.get_x() + i.get_width()
            if self.__can_take_projection['ZY'](k, i) and (i.get_x() + i.get_width() > max_bound['ZY']):
                neweps['ZY'] = (k.get_x(), i.get_y() + i.get_depth(), k.get_z() + k.get_height())
                max_bound['ZY'] = i.get_y() + i.get_depth()


        self.__eps = sorted(self.__eps.union(set(neweps.values())), key=lambda q: (q[2], q[1], q[0]))
        self.__items.add(i)
        self.update_rs(k)
        
    def update_rs(self, k):
        for ep in self.__eps:
            ep.update_rs(k)

def can_take_projections_yx(item1, item2):
    return bool(item1.x >= item2.x + item2.w 
                and item1.get_y() + item1.get_depth() < item2.get_y() +item2.get_depth() 
                and item1.get_z() < item2.get_z() + item2.get_height())
    
def can_take_projections_yz(item1, item2):
    return bool(item1.get_z() >= item2.get_z() + item2.get_height() 
                and item1.get_y() + item1.get_depth() < item2.get_y() +item2.get_depth() 
                and item1.get_x() < item2.get_x() + item2.get_width())

def can_take_projections_xy(item1, item2):
    return bool(item1.get_y() >= item2.get_y() + item2.get_depth() 
                and item1.get_x() + item1.get_width() < item2.get_x() +item2.get_width() 
                and item1.get_z() < item2.get_z() + item2.get_height())

def can_take_projections_xz(item1, item2):
    return bool(item1.get_z() >= item2.get_z() + item2.get_height() 
                and item1.get_x() + item1.get_widtht() < item2.get_x() +item2.get_width() 
                and item1.get_y() < item2.get_y() + item2.get_depth())
    
def can_take_projections_zx(item1, item2):
    return bool(item1.get_x() >= item2.get_x() + item2.get_width() 
                and item1.get_z() + item1.get_height() < item2.get_z() +item2.get_height() 
                and item1.get_y() < item2.get_y() + item2.get_depth())

def can_take_projections_zy(item1, item2):
    return bool(item1.get_y() >= item2.get_y() + item2.get_height() 
                and item1.get_z() + item1.get_height() < item2.get_z() +item2.get_height() 
                and item1.get_x() < item2.get_x() + item2.get_width())