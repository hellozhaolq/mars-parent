package com.zhaolq.mars.common.core.io.file.visitor;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.io.file.CopyDirectoryVisitor;
import org.apache.commons.io.file.Counters.PathCounters;
import org.apache.commons.io.file.CountingPathVisitor;
import org.apache.commons.io.file.PathFilter;
import org.apache.commons.io.file.PathUtils;

/**
 * 将源目录移动到目标目录。与 {@link CopyDirectoryVisitor} 的唯一不同是 copy 方法改成 move 方法
 *
 * @Author zhaolq
 * @Date 2023/6/13 16:46:14
 */
public class MoveDirectoryVisitor extends CountingPathVisitor {

    private static CopyOption[] toCopyOption(final CopyOption... copyOptions) {
        return copyOptions == null ? PathUtils.EMPTY_COPY_OPTIONS : copyOptions.clone();
    }

    private final CopyOption[] copyOptions;
    private final Path sourceDirectory;
    private final Path targetDirectory;

    public MoveDirectoryVisitor(final PathCounters pathCounter, final Path sourceDirectory, final Path targetDirectory, final CopyOption... copyOptions) {
        super(pathCounter);
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
        this.copyOptions = toCopyOption(copyOptions);
    }

    public MoveDirectoryVisitor(
            final PathCounters pathCounter, final PathFilter fileFilter, final PathFilter dirFilter, final Path sourceDirectory,
            final Path targetDirectory, final CopyOption... copyOptions) {
        super(pathCounter, fileFilter, dirFilter);
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
        this.copyOptions = toCopyOption(copyOptions);
    }

    protected void move(final Path sourceFile, final Path targetFile) throws IOException {
        Files.move(sourceFile, targetFile, copyOptions);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MoveDirectoryVisitor other = (MoveDirectoryVisitor) obj;
        return Arrays.equals(copyOptions, other.copyOptions) && Objects.equals(sourceDirectory, other.sourceDirectory)
               && Objects.equals(targetDirectory, other.targetDirectory);
    }

    public CopyOption[] getCopyOptions() {
        return copyOptions.clone();
    }

    public Path getSourceDirectory() {
        return sourceDirectory;
    }

    public Path getTargetDirectory() {
        return targetDirectory;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(copyOptions);
        result = prime * result + Objects.hash(sourceDirectory, targetDirectory);
        return result;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path directory, final BasicFileAttributes attributes)
            throws IOException {
        final Path newTargetDir = resolveRelativeAsString(directory);
        if (Files.notExists(newTargetDir)) {
            Files.createDirectory(newTargetDir);
        }
        return super.preVisitDirectory(directory, attributes);
    }

    private Path resolveRelativeAsString(final Path directory) {
        return targetDirectory.resolve(sourceDirectory.relativize(directory).toString());
    }

    @Override
    public FileVisitResult visitFile(final Path sourceFile, final BasicFileAttributes attributes) throws IOException {
        final Path targetFile = resolveRelativeAsString(sourceFile);
        move(sourceFile, targetFile);
        return super.visitFile(targetFile, attributes);
    }
}
