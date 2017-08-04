import sys;

print "Let's build a model"

if(len(sys.argv) != 3):
    print "Please provide 2 arguments, input file and output file"
    exit();

in_file = str(sys.argv[1])
out_file = str(sys.argv[2])
num_partitions = int(sys.argv[3])

header = [];
partitions = [];
for i in range(num_partitions):
    partitions.append([])

with open(in_file) as file:
    reader = csv.reader(file)
    header = reader.next()
    for row in reader:
        print "Input has a line"
