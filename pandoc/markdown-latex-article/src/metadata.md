---
# Important stuff
title: This is the title
subtitle: This is the subtitle
author: 
  - Florent Dufour
date: \today
subject: The subject
keywords: [Artificial Intelligence, Confidential Computing, Big Data, Medical Informatics, Federated Learning, Zero-trust, Encryption, Data Protection]
abstract: \input{src/abstract.tex}

# Pandoc and LaTeX stuff
geometry: a4paper
toc: true
toc-depth: 2
numbersections: true
lang: en
urlcolor: blue
linkcolor: blue
citeproc: true
bibliography: src/biblio.bib
csl: resources/ieee
filters:
  - citeproc
cite-method: citeproc
linestretch: 1.25
hyperrefoptions:
  - pdfpagemode=FullScreen
header-includes:
  - \usepackage{pgfgantt}
  - \RequirePackage[inline]{enumitem}
  - \renewcommand{\labelitemi}{--}

# Pandoc stuff
self-contained: true
standalone: true
---