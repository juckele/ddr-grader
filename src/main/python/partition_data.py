import csv;
import random;
import sys;

in_file = str(sys.argv[1])
out_file = str(sys.argv[2])
num_partitions = int(sys.argv[3])

header = [];
partitions = [];
for i in range(num_partitions):
    partitions.append([])

# Load all the training rows
row_num = 0;
with open(in_file) as file:
    reader = csv.reader(file);
    header = reader.next();
    for row in reader:
        partitions[row_num % num_partitions].append(row);
        row_num += 1;

# Write test and train files for k partitions
for i in range(num_partitions):
    train_rows = []
    test_rows = partitions[i];
    for j in range(num_partitions):
        if i != j:
            for row in partitions[j]:
                train_rows.append(row);

    with open(out_file+'_k'+str(i+1)+'_train.csv', 'wb') as ofile:
        writer = csv.writer(ofile)
        writer.writerow(header)
        for row in train_rows:
            writer.writerow(row)

    with open(out_file+'_k'+str(i+1)+'_test.csv', 'wb') as ofile:
        writer = csv.writer(ofile)
        writer.writerow(header)
        for row in test_rows:
            writer.writerow(row)
