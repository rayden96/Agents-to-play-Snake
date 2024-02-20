import numpy as np
import scipy.stats as stats

model_names = ["PSOTrained", "GPFirst", "GPSecond", "GPThird"]# "randomizer", "towardslessdense", "wallhugger",5
# Load the data
dataBoardSize3Score = []
dataBoardSize4Score = []
dataBoardSize5Score = []
dataBoardSize6Score = []
dataBoardSize7Score = []
dataBoardSize8Score = []
dataBoardSize9Score = []
dataBoardSize40score = []
meansScore = []
stdsScore = []

dataBoardSize3numMoves = []
dataBoardSize4numMoves = []
dataBoardSize5numMoves = []
dataBoardSize6numMoves = []
dataBoardSize7numMoves = []
dataBoardSize8numMoves = []
dataBoardSize9numMoves = []
dataBoardSize40numMoves = []
meansnumMoves = []
stdsnumMoves = []

dataBoardSize3Wins = []
dataBoardSize4Wins = []
dataBoardSize5Wins = []
dataBoardSize6Wins = []
dataBoardSize7Wins = []
dataBoardSize8Wins = []
dataBoardSize9Wins = []
dataBoardSize40Wins = []
meansWins = []
stdsWins = []

for model in model_names:
    #skip the first line
    with open(f"{model}.txt", "r") as f:
        f.readline()
        #read the comma separated values\
        dataBoardSize3Score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize3numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize3Wins.append([float(x) for x in f.readline().split(",")])
        f.readline()
        dataBoardSize4Score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize4numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize4Wins.append([float(x) for x in f.readline().split(",")])
        f.readline()
        dataBoardSize5Score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize5numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize5Wins.append([float(x) for x in f.readline().split(",")])
        f.readline()
        dataBoardSize6Score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize6numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize6Wins.append([float(x) for x in f.readline().split(",")])
        f.readline()
        dataBoardSize7Score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize7numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize7Wins.append([float(x) for x in f.readline().split(",")])
        f.readline()
        dataBoardSize8Score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize8numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize8Wins.append([float(x) for x in f.readline().split(",")])
        f.readline()
        dataBoardSize9Score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize9numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize9Wins.append([float(x) for x in f.readline().split(",")])
        f.readline()
        dataBoardSize40score.append([float(x) for x in f.readline().split(",")])
        dataBoardSize40numMoves.append([float(x) for x in f.readline().split(",")])
        dataBoardSize40Wins.append([float(x) for x in f.readline().split(",")])

# #write all these reuslts to a csv
# with open("results.csv", "w") as f:
#     f.write("Board Size, Model, Score Mean, Score Standard Deviation, numMoves mean, numMoves Standard Deviation, Wins Mean, Wins Standard Deviation\n")
#     for i in range(len(model_names)):
#         f.write("3, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize3Score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize3Score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize3numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize3numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize3Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize3Wins[i]), 3)}\n")
#         f.write("4, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize4Score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize4Score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize4numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize4numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize4Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize4Wins[i]), 3)}\n")
#         f.write("5, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize5Score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize5Score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize5numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize5numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize5Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize5Wins[i]), 3)}\n")
#         f.write("6, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize6Score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize6Score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize6numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize6numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize6Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize6Wins[i]), 3)}\n")
#         f.write("7, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize7Score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize7Score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize7numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize7numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize7Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize7Wins[i]), 3)}\n")
#         f.write("8, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize8Score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize8Score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize8numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize8numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize8Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize8Wins[i]), 3)}\n")
#         f.write("9, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize9Score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize9Score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize9numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize9numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize9Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize9Wins[i]), 3)}\n")
#         f.write("40, ")
#         f.write(f"{model_names[i]}, ")
#         f.write(f"{round(np.mean(dataBoardSize40score[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize40score[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize40numMoves[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize40numMoves[i]), 3)}, ")
#         f.write(f"{round(np.mean(dataBoardSize40Wins[i]), 3)}, ")
#         f.write(f"{round(np.std(dataBoardSize40Wins[i]), 3)}\n")




            
#get input from the user for board size and which metric to use
boardSize = int(input("Enter the board size: "))
metric = input("Enter the metric to use (score, numMoves, wins): ")

#set the data to use based on the user input
if boardSize == 3:
    if metric == "score":
        data = dataBoardSize3Score
    elif metric == "numMoves":
        data = dataBoardSize3numMoves
    elif metric == "wins":
        data = dataBoardSize3Wins
    else:
        print("Invalid metric")
        exit()
elif boardSize == 4:
    if metric == "score":
        data = dataBoardSize4Score
    elif metric == "numMoves":
        data = dataBoardSize4numMoves
    elif metric == "wins":
        data = dataBoardSize4Wins
    else:
        print("Invalid metric")
        exit()
elif boardSize == 5:
    if metric == "score":
        data = dataBoardSize5Score
    elif metric == "numMoves":
        data = dataBoardSize5numMoves
    elif metric == "wins":
        data = dataBoardSize5Wins
    else:
        print("Invalid metric")
        exit()
elif boardSize == 6:
    if metric == "score":
        data = dataBoardSize6Score
    elif metric == "numMoves":
        data = dataBoardSize6numMoves
    elif metric == "wins":
        data = dataBoardSize6Wins
    else:
        print("Invalid metric")
        exit()
elif boardSize == 7:
    if metric == "score":
        data = dataBoardSize7Score
    elif metric == "numMoves":
        data = dataBoardSize7numMoves
    elif metric == "wins":
        data = dataBoardSize7Wins
    else:
        print("Invalid metric")
        exit()
elif boardSize == 8:
    if metric == "score":
        data = dataBoardSize8Score
    elif metric == "numMoves":
        data = dataBoardSize8numMoves
    elif metric == "wins":
        data = dataBoardSize8Wins
    else:
        print("Invalid metric")
        exit()
elif boardSize == 9:
    if metric == "score":
        data = dataBoardSize9Score
    elif metric == "numMoves":
        data = dataBoardSize9numMoves
    elif metric == "wins":
        data = dataBoardSize9Wins
    else:
        print("Invalid metric")
        exit()
elif boardSize == 40:
    if metric == "score":
        data = dataBoardSize40score
    elif metric == "numMoves":
        data = dataBoardSize40numMoves
    elif metric == "wins":
        data = dataBoardSize40Wins
    else:
        print("Invalid metric")
        exit()
else:
    print("Invalid board size")
    exit()

#calculate the means and standard deviations
means = [np.mean(x) for x in data]
stds = [np.std(x) for x in data]

# Print the means and standard deviations4
print("Means:")
for i in range(len(model_names)):
    print(f"{model_names[i]}: {means[i]}")
print("Standard deviations:")
for i in range(len(model_names)):
    print(f"{model_names[i]}: {stds[i]}")

#perform the man whitney u test with bonferroni correction
alpha = 0.05
alpha = alpha / len(model_names)
print(f"alpha: {alpha}")
print("p-values:")
for i in range(len(model_names)):
    for j in range(i+1, len(model_names)):
        print(f"{model_names[i]} vs {model_names[j]}: {stats.mannwhitneyu(data[i], data[j])[1]}")
        if stats.mannwhitneyu(data[i], data[j])[1] < alpha:
            #print the p-value
            print(stats.mannwhitneyu(data[i], data[j])[1])
            print(f"{model_names[i]} and {model_names[j]} are significantly different")
            #print the better model
            if means[i] > means[j]:
                print(f"{model_names[i]} is better")
            else:
                print(f"{model_names[j]} is better")
        else:
            print(f"{model_names[i]} and {model_names[j]} are not significantly different")
        print()



