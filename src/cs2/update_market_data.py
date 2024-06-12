from marketdata import get_item_data
import re

FILENAME = "skinsdb.csv"
HEADER = "skinName,minFloat,maxFloat,collection,grade,fnValue,mwValue,ftValue,wwValue,bsValue,fnValue_st,mwValue_st,ftValue_st,wwValue_st,bsValue_st"


def update_data():
    with open(FILENAME+"1.csv", 'a', encoding="UTF-8") as writefile:
        with open(FILENAME, 'r', encoding="UTF-8") as datafile:
            for line in datafile:
                line = line.rstrip()
                result_line = line
                if "skinName,minFloat" not in line:
                    line = re.sub(",[^A-Z]*$", "", line)
                    weapon_skin = line.split("|")
                    weapon = weapon_skin[0].strip()
                    skin = weapon_skin[1][:weapon_skin[1].find(",")].strip()

                    for i in range(2):
                        for wear in range(5):
                            result_line += ',' + str(get_item_data(weapon, skin, wear + 1, i)["sell_req"])

                    result_line.rstrip(',')
                writefile.write(result_line+'\n')


if __name__ == "__main__":
    update_data()
