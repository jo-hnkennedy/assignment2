use List::Util qw/shuffle/;

open(my $fh, "words");
my @lines = <$fh>;
close($fh);

@lines = shuffle(@lines);

open(my $gh, ">As2Large.txt");
foreach (@lines) {
	print $gh "$_";
}
close($gh);
