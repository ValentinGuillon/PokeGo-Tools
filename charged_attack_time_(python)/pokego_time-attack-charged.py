#Pokémon Go
# Ce programme affiche le temps max
# pour charger une attaque chargée
# en fonction de l'attaque immédiate


LOC = './' #location for the used files
CHARGE_MOVES_FILENAME = 'moves_charge.json'
FAST_MOVES_FILENAME = 'moves_fast.json'

import json
from math import ceil

ATTACK_IMMEDIATE = {}
ATTACK_CHARGED = {}


def format_string(string: str, ignore = False) -> str:
    string = string.lower()
    string = string.replace('é', 'e')
    string = string.replace('è', 'e')
    string = string.replace('ê', 'e')
    string = string.replace('à', 'a')
    string = string.replace('â', 'a')
    string = string.replace('ô', 'o')
    string = string.replace('û', 'u')
    string = string.replace("'", ' ')
    string = string.replace("œ", 'oe')

    if not ignore:
        string = string.replace('-', ' ')
    return string



def extract_attacks():
    filenames = ['immediate', 'charged']
    for type in filenames:
        if type == 'immediate':
            filename = LOC + FAST_MOVES_FILENAME
        elif type == 'charged':
            filename = LOC + CHARGE_MOVES_FILENAME

        with open(filename, 'r') as file:
                data:list = json.load(file)
                for move in data:
                    name = format_string(move['name'])
                    if type == 'immediate':
                        ATTACK_IMMEDIATE[name] = move
                    elif type == 'charged':
                        ATTACK_CHARGED[name] = move



def is_float(val) -> bool:
    val = float(val)
    if val < 0:
        val *= -1
    
    while val >= 1:
        val -= 1
    
    if val:
        return True
    return False





def add_details(times) -> dict:
    copy = times
    for couple, datas in copy.items():
        couple_splitted = couple[1:].split(' + ')
        immediate = format_string(couple_splitted[0])
        charged = format_string(couple_splitted[1])

        #get details
        duree = ATTACK_IMMEDIATE[immediate]['duree']
        dps_immediate = ATTACK_IMMEDIATE[immediate]['degats'] / duree
        eps_immediate = ATTACK_IMMEDIATE[immediate]['energie'] / duree
        degats_charged = ATTACK_CHARGED[charged]['degats']
        energie_charged = ATTACK_CHARGED[charged]['energie']

        #prepare details
        degats_charged = int(degats_charged)
        energie_charged = int(energie_charged)

        #set output
        text = ''
        # if float(dps_immediate) >= 10:
        #     text += f'{dps_immediate:>5}dps '
        # else:
        #     text += f'{dps_immediate:>4}dps '

        # if float(eps_immediate) >= 10:
        #     text += f'{eps_immediate:>5}eps'
        # else:
        #     text += f'{eps_immediate:>4}eps'
        if is_float(dps_immediate):
            text += f'{dps_immediate:.1f} dps '
        else:
            text += f'{int(dps_immediate)} dps '

        if is_float(eps_immediate):
            text += f'{eps_immediate:.1f} eps'
        else:
            text += f'{int(eps_immediate)} eps'

        text += f'  +  {degats_charged}d {energie_charged}e'

        times[couple]['details'] = text

    return times





def calcul_full_charged(time_immediate, energy_immediate, energy_charged) -> float:
    try:
        number_of_attack = ceil(energy_charged / energy_immediate)
    except:
        number_of_attack = 100
    time = number_of_attack * time_immediate
    return time



def calcul_times_charged(attacks_immediate, attacks_charged) -> dict:
    times = {}
    for immediate in attacks_immediate:
        for charged in attacks_charged:
            if charged == 'f':
                charged = 'frustration'
            couple = ATTACK_IMMEDIATE[immediate]['name'] + ' + ' + ATTACK_CHARGED[charged]['name']
            if ATTACK_CHARGED[charged]['bonus'] != 'none':
                couple = '*' + couple
            else:
                couple = ' ' + couple
            time = calcul_full_charged(ATTACK_IMMEDIATE[immediate]['duree'], ATTACK_IMMEDIATE[immediate]['energie'], ATTACK_CHARGED[charged]['energie'])
            times[couple] = {'time': time}
    
    return times





def test():
    a = list(ATTACK_IMMEDIATE.keys())
    #b = ['poing ombre'] #35
    #b = ['revenant'] #90
    b = ['lutte'] #100
    return a, b



def attacks_times():
    try:
        print('\nAttaque(s) immédiate(s):') #(séparé par DEUX espaces)
        user_input = input('>')
        user_input = format_string(user_input)
        immediate = user_input.split('  ')
        print('\nAttaque(s) chargées(s):') #(séparé par DEUX espaces)
        user_input = input('>')
        user_input = format_string(user_input)
        charged = user_input.split('  ')

    except:
        print('Input error.')

    #immediate, charged = test()
    nb_charged = len(charged)

    try:
        times = calcul_times_charged(immediate, charged)
        times_with_details = add_details(times)

        print()
        for i, (couple, datas) in enumerate(times_with_details.items(), start=1):
            time = f'{datas["time"]:.1f}'
            print(f' {time:>4}s {couple}')
            print(f'          {datas["details"]}')
            if (i % nb_charged == 0) and (i != len(times_with_details)):
                print()

    except KeyError as key:
        input(f"\nL'attaque {key} n'existe pas.\n...")





def show_attacks():
    #prepare lists
    attacks_immediate = []
    attacks_charged = []
    keys_immediate = []
    keys_charged = []

    for _attack, datas in ATTACK_IMMEDIATE.items():
        attacks_immediate.append(list(datas.values()))
    keys_immediate = ATTACK_IMMEDIATE[_attack].keys()
    for _attack, datas in ATTACK_CHARGED.items():
        attacks_charged.append(list(datas.values()))
    keys_charged = ATTACK_CHARGED[_attack].keys()
    
    
    while True:
        #user input
        print('\n0 exit, 1 immediates, 2 chargées')
        
        user_input = input('>')
        try:
            user_input = int(user_input)
        except:
            print('\nMauvais input.')
            continue

        attacks = []
        keys = []

        #list choice
        if user_input == 0:
            return
        elif user_input == 1:
            attacks = attacks_immediate
            keys = keys_immediate
        elif user_input == 2:
            attacks = attacks_charged
            keys = keys_charged
        else:
            pass

        #prepare prints
        temp = ''
        for i, key in enumerate(keys):
            temp += key
            if i == 0:
                temp += '\n  '
            else:
                temp += ' '

        #print attacks
        print(temp)
        for attack in attacks:
            for i, val in enumerate(attack):
                if val == 'none':
                    continue
                print(val, end='')
                if i == 0:
                    print('\n  ', end='')
                elif i != len(attack) -1:
                    print('\t', end='')
            print()
        print(temp)








def main():
    extract_attacks()

    print('Vous pouvez donnez plusieurs attaques.\nIl faut simplement les séparer par DEUX espaces.')
    print('Vous pouvez ignorez les accents, tirets et apostrophe.')

    mod = 1

    while True:
        print("\n\nPour voir les attaques, écrivez n'importe quoi. (0 pour quitter)")
        user_input = input(f'...')
        if user_input == '0':
            break
        elif user_input != '':
            mod = 2

        if mod == 1:
            attacks_times()
        elif mod == 2:
            show_attacks()
            mod = 1
        else:
            input('\nMode non existant.\n...')
            mod = 1





if __name__ == '__main__':
    main()
    print('\nEnd Prog.')

