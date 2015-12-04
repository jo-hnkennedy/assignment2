use warnings;
use strict;

my @files = qw/
1000.txt
10000.txt
100000.txt
150000.txt
20000.txt
200000.txt
40000.txt
500.txt
5000.txt
50000.txt
60000.txt
7500.txt
75000.txt /;

my @wordCounts;
my @comparisons;
my @swaps;
my @times;

foreach my $file (@files) {
	my $output = `java mergeTest $file`;
	my @outputLines = split(/\n/, $output);
	push(@wordCounts, $outputLines[0]);	
	push(@comparisons, $outputLines[1]);
	push(@swaps, $outputLines[2]);
	push(@times, $outputLines[3]);
}

for (my $i = 0; $i < scalar @wordCounts; $i++) {
	print $wordCounts[$i] . ", " . $comparisons[$i] . ", " . $swaps[$i] . ", " . $times[$i], "\n";
}	
		
	
