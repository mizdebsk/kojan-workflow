[![build status](https://img.shields.io/github/actions/workflow/status/mizdebsk/kojan-workflow/ci.yml?branch=master)](https://github.com/mizdebsk/kojan-workflow/actions/workflows/ci.yml?query=branch%3Amaster)
[![License](https://img.shields.io/github/license/mizdebsk/kojan-workflow.svg?label=License)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central version](https://img.shields.io/maven-central/v/io.kojan/kojan-workflow.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.kojan/kojan-workflow)
[![Javadoc](https://javadoc.io/badge2/io.kojan/kojan-workflow/javadoc.svg)](https://javadoc.io/doc/io.kojan/kojan-workflow)

Kojan Workflow
==============

A simple library for concurrently executing interrelated tasks.

The Kojan Workflow library is used to model workflows as sets of
interrelated arbitrary tasks that can be executed concurrently.  Each
task has a handler which is a class implementing code for executing
particular task and a dedicated storage in the file system.  Task
results from previous runs can be reused as long as the previous task
had identical inputs.  Failed tasks can be resumed from the point of
failure.

This is free software. You can redistribute and/or modify it under the
terms of Apache License Version 2.0.

This software was written by Mikolaj Izdebski.
