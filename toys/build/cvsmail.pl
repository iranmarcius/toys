#!/usr/bin/perl

my ($user, $verInfo, $to) = @ARGV;

open(F, "| /bin/mail -s \"[cvs-commit] $user - $verInfo\"  $to");
while (defined($l = <STDIN>)) {
	print F $l;
}
close(F);
