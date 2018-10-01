
// -----------------------------------
// CSCI 340 - Operating Systems
// Fall 2018
// cache.c file
//
// Homework 1
//
// -----------------------------------

#include "cache.h"
#include "memory.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int initializeCache(unsigned int number_of_lines)
{

	int line, retVal;

	retVal = OK;

	cache = malloc(sizeof(cache_line *) * number_of_lines);

	if (cache != NULL)
	{

		for (line = 0; line < number_of_lines; line++)
		{

			cache[line] = (cache_line *)malloc(sizeof(cache_line));
			cache[line]->tag = UNK;
			cache[line]->hit_count = ZERO;
		}
	}
	else
		retVal = FAIL;

	return retVal;

} // end initializeCache function

int cread(unsigned int cmf, unsigned int *hex_addr, unsigned int *found, unsigned int *replace)
{
	char value;

	if (*hex_addr >= 256)
	{
		return FAIL;
	}

	//Direct mapping
	if (cmf == DM)
	{
		unsigned int block_offset = (*hex_addr & 3);
		unsigned int line_bits = ((*hex_addr & 28) >> 2);
		unsigned int tag_bits = ((*hex_addr & 224) >> 5);

		//Cache HIT
		if (cache[line_bits]->tag == tag_bits)
		{
			(*found) = HIT;
			(*replace) = NO;
			value = phy_memory[*block_location + block_offset];
			cache[line_bits]->hit_count++;
		}

		//Cache MISS
		else
		{
			(*found) = MISS;
			cache[line_bits]->hit_count = 1;
			value = phy_memory[*hex_addr];

			//if cache line is empty, no need to replace
			if (cache[line_bits]->tag == UNK)
			{
				(*replace) = NO;
			}
			else
			{
				(*replace) = YES;
			}
			//update tag bits
			cache[line_bits]->tag = tag_bits;
		}
	}

	//Fully associative
	else
	{
		(*found) = NO;
		(*replace) = YES;
		unsigned int block_offset = (*hex_addr & 3);
		int tag_bits = ((*hex_addr & 252) >> 2);

		//Check for cache hit
		for (int line = 0; line < NUM_OF_LINES; line++)
		{
			//Cache HIT
			if (cache[line]->tag == tag_bits)
			{
				(*found) = HIT;
				(*replace) = NO;
				cache[line]->hit_count++;
				value = phy_memory[*block_location + block_offset];
				break;
			}
		}

		//Cache MISS
		if (*found != HIT)
		{
			(*found) = MISS;

			value = phy_memory[*hex_addr];

			//fill first empty line found
			for (int line = 0; line < NUM_OF_LINES; line++)
			{
				if (cache[line]->tag == UNK)
				{
					(*replace) = NO;
					cache[line]->tag = tag_bits;
					cache[line]->hit_count = 1;
					break;
				}
			}

			//if no empty lines, replace via LFU policy
			if ((*replace) != NO)
			{
				(*replace) = YES;

				int min_hit_count = 99999;
				int min_index = 99999;

				for (int line_index = 0; line_index < NUM_OF_LINES; line_index++)
				{
					//check if current index hit count lower than min hit count
					if (cache[line_index]->hit_count < min_hit_count)
					{
						min_index = line_index;
						min_hit_count = cache[line_index]->hit_count;
					}
					//if hit counts equal, check if current index lower
					else if (min_hit_count == cache[line_index]->hit_count)
					{
						//if current index lower, update min index
						if (line_index < min_index)
						{
							min_index = line_index;
						}
					}
				}

				cache[min_index]->tag = tag_bits;
				cache[min_index]->hit_count = 1;
			}
		}
	}

	return value;

} // end cread function

void cprint()
{

	unsigned int line;

	printf("\n---------------------------------------------\n");
	printf("line\ttag\tnum of hits\n");
	printf("---------------------------------------------\n");

	for (line = 0; line < NUM_OF_LINES; line++)
	{

		if (cache[line]->tag == UNK)
		{

			printf("%d\t%d\t%d\n", line, cache[line]->tag, cache[line]->hit_count);
		}
		else
		{

			printf("%d\t%02X\t%d\n", line, cache[line]->tag, cache[line]->hit_count);
		}
	}

} // end cprint function
