import csv;
import math;

in_train_file = str(sys.argv[1])
in_test_file = str(sys.argv[2])
out_train_file = str(sys.argv[3])
out_test_file = str(sys.argv[4])

non_feature_columns = 

# Load train file
train_rows = [];
rownum = 0;
with open('training.csv') as file:
    reader = csv.reader(file);
    for row in reader:
        if rownum == 0:
            train_header = row
            features = len(train_header) - 3
            print features
            tmp = [0 for x in range(features)]
            value_count = [0 for x in range(features)]
            mv_count = [0 for x in range(features)]
            mean = [0 for x in range(features)]
            std_dev = [0 for x in range(features)]
        else:
            train_rows.append(row)

        if rownum < 4:
            print row
        rownum += 1

# Extract mean and missing features indicators
for row in train_rows:
    for i in range(features):
        if row[i+1] != '-999.0':
            tmp[i] += float(row[i+1])
            value_count[i] += 1;
        else:
            mv_count[i] = 1;
for i in range(features):
    mean[i] = tmp[i]/value_count[i]
    tmp[i] = 0

# Extract standard deviation
for row in train_rows:
    for i in range(features):
        if row[i+1] != '-999.0':
            tmp[i] += (float(row[i+1]) - mean[i]) ** 2
for i in range(features):
    std_dev[i] = math.sqrt(tmp[i]/(value_count[i]-1))
    tmp[i] = 0

# Show the user the dervied values
print value_count
print mv_count
print mean
print std_dev

# Load test file
# The values in this file aren't used to determine mean & std.dev.
test_rows = [];
rownum = 0
with open('test.csv') as file:
    reader = csv.reader(file);
    for row in reader:
        if rownum == 0:
            test_header = row
        elif rownum > 0:
            test_rows.append(row)

        if rownum < 4:
            print row;
        rownum += 1


# Create an expanded header with missing value features
new_train_header = []
new_test_header = []
j = 0; # extended index
for i in range(len(train_header)):
    new_train_header.append( train_header[i] )
    new_test_header.append( train_header[i] )
    if i - 1 >= 0 and i < len(mv_count)+1 and mv_count[i-1] > 0:
        j += 1
        new_train_header.append( 'MV_' + train_header[i] )
        new_test_header.append( 'MV_' + train_header[i] )
    j += 1

# Remove the last two items from the extended header for the new test headers
new_test_header.pop(); new_test_header.pop();
print train_header
print new_train_header
print test_header
print new_test_header

# Apply feature scaling and missing value extraction on training data
scaled_train_rows = []
for row in train_rows:
    # Make a new row
    scaled_row = []
    for i in range(len(train_header)-1):
        # record if the raw value is missing
        if row[i] == '-999.0':
            value_missing = True
            row[i] = mean[i-1]
        else:
            value_missing = False
        # Place the scaled value of i into j if this is a feature
        if i >= 1 and i <= features:
            scaled_row.append((float(row[i])-mean[i-1])/std_dev[i-1])
        # Otherwise just copy the raw data
        else:
            scaled_row.append(row[i])

        # If we having a missing value column, fill that out too
        if i - 1 >= 0 and i < len(mv_count)+1 and mv_count[i-1] > 0:
            scaled_row.append(1 if value_missing else 0)
    # Change the labels on the rows from 's' and 'b' to 1 and 0
    if row[i+1] == 's':
        scaled_row.append(1);
    else:
        scaled_row.append(0);
    scaled_train_rows.append(scaled_row)

# Apply feature scaling and missing value extraction on test data
scaled_test_rows = []
for row in test_rows:
    # Make a new row
    scaled_row = []
    for i in range(len(test_header)):
        # record if the raw value is missing
        if row[i] == '-999.0':
            value_missing = True
            row[i] = mean[i-1]
        else:
            value_missing = False
        # Place the scaled value of i into j if this is a feature
        if i >= 1 and i <= features:
            scaled_row.append((float(row[i])-mean[i-1])/std_dev[i-1])
        # Otherwise just copy the raw data
        else:
            scaled_row.append(row[i])

        # If this is a missing value feature, store that value
        if i - 1 >= 0 and i < len(mv_count)+1 and mv_count[i-1] > 0:
            scaled_row.append(1 if value_missing else 0)
    scaled_test_rows.append(scaled_row)

for i in range(4):
    print scaled_train_rows[i]

for i in range(4):
    print scaled_test_rows[i]

# Write the scaled CSV files
with open('train_z.csv', 'wb') as ofile:
    writer = csv.writer(ofile)
    writer.writerow(new_train_header)
    for row in scaled_train_rows:
        writer.writerow(row)

with open('test_z.csv', 'wb') as ofile:
    writer = csv.writer(ofile)
    writer.writerow(new_test_header)
    for row in scaled_test_rows:
        writer.writerow(row)


