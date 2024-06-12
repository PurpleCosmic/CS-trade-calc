import requests
import re

FILENAME = "skinsdb.csv"
LINK = "https://market.csgo.com/api/v2/prices/EUR.json"


def get_item_prices():
    items = requests.get(LINK).json()["items"]
    return {i['market_hash_name']: float(i['price']) for i in items}


wearToName = {
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
    res += f"{weapon} | {skin} ({wearToName[wear]})"
    return res


def get_price(weapon, skin, wear, stat, prices):
    return prices[get_hash_name(weapon, skin, wear, stat)]


if __name__ == "__main__":
    print(get_price("P2000", "Obsidian", 1, 1, get_item_prices()))
