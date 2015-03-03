library(polyester)
library(Biostrings)

lib_size = 15000000

fold_changes = matrix(c(4,4,rep(1,18),1,1,4,4,rep(1,16)), nrow=20)
head(fold_changes)
fasta_file = "genomes/chr22_transcripts.fa"
#fasta_file = system.file('extdata', 'chr22.fa', package='polyester')
fasta = readDNAStringSet(fasta_file)

fold_changes = function(n) {
    c1 = rep(0, n)
    c2 = rnorm(n, 0, 1)
    return(2^cbind(c1, c2))
}
fc = fold_changes(length(fasta))
names(x) = names(fasta)
# average depth of 20 per base
readspertx = 20 * width(fasta)
readspertx = round(readspertx * (lib_size / sum(readspertx)))
simulate_experiment(fasta_file, reads_per_transcript=readspertx, 
    num_reps=c(10,10), fold_changes=fc, outdir='simulated_reads') 
