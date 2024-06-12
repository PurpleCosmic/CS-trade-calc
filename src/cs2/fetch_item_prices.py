import requests

FILENAME = "skinsdb.csv"
LINK = "https://market.csgo.com/api/v2/prices/EUR.json"


def get_item_prices():
    items = requests.get(LINK).json()["items"]
    return {i['market_hash_name']: float(i['price']) for i in items}


wear_dict = {
    1: "Factory New",
    2: "Minimal Wear",
    3: "Field-Tested",
    4: "Well-Worn",
    5: "Battle-Scarred"
}


def get_hash_name(weapon, skin, wear, stat):
    res = ""
    if stat == 1:
        res = "StatTrak™ " + res
    res += f"{weapon} | {skin} ({wear_dict[wear]})"
    return res


def get_price(weapon, skin, wear, stat, prices):
    hash_name = get_hash_name(weapon, skin, wear, stat)
    if hash_name in prices:
        return prices[hash_name]
    else:
        print(weapon, skin)
        return 0


if __name__ == "__main__":
    print(get_item_prices())
    print(get_price("Negev", "Anodized Navy", 1, 0, get_item_prices()))
