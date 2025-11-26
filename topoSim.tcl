set ns [new Simulator]
set nf [open out.nam w]
$ns namtrace-all $nf

set tf [open out.tr w]
$ns trace-all $tf

$ns color 1 Blue
$ns color 2 Red

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

$ns duplex-link $n0 $n2 1.5Mb 15ms DropTail
$ns duplex-link $n1 $n2 1.5Mb 15ms DropTail
$ns duplex-link $n2 $n3 2Mb 15ms DropTail
$ns queue-limit $n2 $n3 20

$ns at 0.0 "$n0 label FTP-Source-1"
$ns at 0.0 "$n1 label FTP-Source-2"
$ns at 0.0 "$n2 label Router"
$ns at 0.0 "$n3 label Destination"

$ns duplex-link-op $n0 $n2 orient right-down
$ns duplex-link-op $n1 $n2 orient right-up
$ns duplex-link-op $n2 $n3 orient right

set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0
$tcp0 set fid_ 1
set sink0 [new Agent/TCPSink]
$ns attach-agent $n3 $sink0
$ns connect $tcp0 $sink0
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

set tcp1 [new Agent/TCP]
$ns attach-agent $n1 $tcp1
$tcp1 set fid_ 2
set sink1 [new Agent/TCPSink]
$ns attach-agent $n3 $sink1
$ns connect $tcp1 $sink1
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1

$ns at 0.2 "$ftp0 start"
$ns at 0.3 "$ftp1 start"
$ns at 4.0 "$ftp0 stop"
$ns at 4.2 "$ftp1 stop"

proc finish {} {
	global ns nf tf
	$ns flush-trace
	close $nf
	close $tf
	puts "Running NAM..."
	exec nam out.nam &
	exit 0
}

$ns at 4.3 "finish"
puts "Starting Simulation..."
$ns run






